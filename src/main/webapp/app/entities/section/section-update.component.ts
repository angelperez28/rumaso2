import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISection, Section } from 'app/shared/model/section.model';
import { SectionService } from './section.service';
import { IProfessor } from 'app/shared/model/professor.model';
import { ProfessorService } from 'app/entities/professor/professor.service';
import { IGrades } from 'app/shared/model/grades.model';
import { GradesService } from 'app/entities/grades/grades.service';
import { ICoursePage } from 'app/shared/model/course-page.model';
import { CoursePageService } from 'app/entities/course-page/course-page.service';

@Component({
  selector: 'jhi-section-update',
  templateUrl: './section-update.component.html'
})
export class SectionUpdateComponent implements OnInit {
  isSaving: boolean;

  professors: IProfessor[];

  grades: IGrades[];

  coursepages: ICoursePage[];

  editForm = this.fb.group({
    id: [],
    courseName: [],
    semester: [],
    year: [],
    department: [],
    section: [],
    professor: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sectionService: SectionService,
    protected professorService: ProfessorService,
    protected gradesService: GradesService,
    protected coursePageService: CoursePageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ section }) => {
      this.updateForm(section);
    });
    this.professorService
      .query({ filter: 'section-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IProfessor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProfessor[]>) => response.body)
      )
      .subscribe(
        (res: IProfessor[]) => {
          if (!this.editForm.get('professor').value || !this.editForm.get('professor').value.id) {
            this.professors = res;
          } else {
            this.professorService
              .find(this.editForm.get('professor').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IProfessor>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IProfessor>) => subResponse.body)
              )
              .subscribe(
                (subRes: IProfessor) => (this.professors = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.gradesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IGrades[]>) => mayBeOk.ok),
        map((response: HttpResponse<IGrades[]>) => response.body)
      )
      .subscribe((res: IGrades[]) => (this.grades = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.coursePageService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICoursePage[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICoursePage[]>) => response.body)
      )
      .subscribe((res: ICoursePage[]) => (this.coursepages = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(section: ISection) {
    this.editForm.patchValue({
      id: section.id,
      courseName: section.courseName,
      semester: section.semester,
      year: section.year,
      department: section.department,
      section: section.section,
      professor: section.professor
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const section = this.createFromForm();
    if (section.id !== undefined) {
      this.subscribeToSaveResponse(this.sectionService.update(section));
    } else {
      this.subscribeToSaveResponse(this.sectionService.create(section));
    }
  }

  private createFromForm(): ISection {
    return {
      ...new Section(),
      id: this.editForm.get(['id']).value,
      courseName: this.editForm.get(['courseName']).value,
      semester: this.editForm.get(['semester']).value,
      year: this.editForm.get(['year']).value,
      department: this.editForm.get(['department']).value,
      section: this.editForm.get(['section']).value,
      professor: this.editForm.get(['professor']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISection>>) {
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

  trackProfessorById(index: number, item: IProfessor) {
    return item.id;
  }

  trackGradesById(index: number, item: IGrades) {
    return item.id;
  }

  trackCoursePageById(index: number, item: ICoursePage) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
