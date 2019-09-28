import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProfessor, Professor } from 'app/shared/model/professor.model';
import { ProfessorService } from './professor.service';
import { ISection } from 'app/shared/model/section.model';
import { SectionService } from 'app/entities/section/section.service';

@Component({
  selector: 'jhi-professor-update',
  templateUrl: './professor-update.component.html'
})
export class ProfessorUpdateComponent implements OnInit {
  isSaving: boolean;

  sections: ISection[];

  editForm = this.fb.group({
    id: [],
    name: [],
    department: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected professorService: ProfessorService,
    protected sectionService: SectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ professor }) => {
      this.updateForm(professor);
    });
    this.sectionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISection[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISection[]>) => response.body)
      )
      .subscribe((res: ISection[]) => (this.sections = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(professor: IProfessor) {
    this.editForm.patchValue({
      id: professor.id,
      name: professor.name,
      department: professor.department
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const professor = this.createFromForm();
    if (professor.id !== undefined) {
      this.subscribeToSaveResponse(this.professorService.update(professor));
    } else {
      this.subscribeToSaveResponse(this.professorService.create(professor));
    }
  }

  private createFromForm(): IProfessor {
    return {
      ...new Professor(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      department: this.editForm.get(['department']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfessor>>) {
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
