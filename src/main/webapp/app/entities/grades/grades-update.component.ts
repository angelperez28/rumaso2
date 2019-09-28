import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IGrades, Grades } from 'app/shared/model/grades.model';
import { GradesService } from './grades.service';
import { ISection } from 'app/shared/model/section.model';
import { SectionService } from 'app/entities/section/section.service';

@Component({
  selector: 'jhi-grades-update',
  templateUrl: './grades-update.component.html'
})
export class GradesUpdateComponent implements OnInit {
  isSaving: boolean;

  sections: ISection[];

  editForm = this.fb.group({
    id: [],
    aNumber: [],
    bNumber: [],
    cNumber: [],
    dNumber: [],
    fNumber: [],
    wNumber: [],
    studentNumber: [],
    section: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected gradesService: GradesService,
    protected sectionService: SectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ grades }) => {
      this.updateForm(grades);
    });
    this.sectionService
      .query({ filter: 'grades-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ISection[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISection[]>) => response.body)
      )
      .subscribe(
        (res: ISection[]) => {
          if (!this.editForm.get('section').value || !this.editForm.get('section').value.id) {
            this.sections = res;
          } else {
            this.sectionService
              .find(this.editForm.get('section').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ISection>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ISection>) => subResponse.body)
              )
              .subscribe(
                (subRes: ISection) => (this.sections = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(grades: IGrades) {
    this.editForm.patchValue({
      id: grades.id,
      aNumber: grades.aNumber,
      bNumber: grades.bNumber,
      cNumber: grades.cNumber,
      dNumber: grades.dNumber,
      fNumber: grades.fNumber,
      wNumber: grades.wNumber,
      studentNumber: grades.studentNumber,
      section: grades.section
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const grades = this.createFromForm();
    if (grades.id !== undefined) {
      this.subscribeToSaveResponse(this.gradesService.update(grades));
    } else {
      this.subscribeToSaveResponse(this.gradesService.create(grades));
    }
  }

  private createFromForm(): IGrades {
    return {
      ...new Grades(),
      id: this.editForm.get(['id']).value,
      aNumber: this.editForm.get(['aNumber']).value,
      bNumber: this.editForm.get(['bNumber']).value,
      cNumber: this.editForm.get(['cNumber']).value,
      dNumber: this.editForm.get(['dNumber']).value,
      fNumber: this.editForm.get(['fNumber']).value,
      wNumber: this.editForm.get(['wNumber']).value,
      studentNumber: this.editForm.get(['studentNumber']).value,
      section: this.editForm.get(['section']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrades>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSectionById(index: number, item: ISection) {
    return item.id;
  }
}
