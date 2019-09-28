import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICoursePage, CoursePage } from 'app/shared/model/course-page.model';
import { CoursePageService } from './course-page.service';
import { ISection } from 'app/shared/model/section.model';
import { SectionService } from 'app/entities/section/section.service';

@Component({
  selector: 'jhi-course-page-update',
  templateUrl: './course-page-update.component.html'
})
export class CoursePageUpdateComponent implements OnInit {
  isSaving: boolean;

  sections: ISection[];

  editForm = this.fb.group({
    id: [],
    rating: [],
    sections: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected coursePageService: CoursePageService,
    protected sectionService: SectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ coursePage }) => {
      this.updateForm(coursePage);
    });
    this.sectionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISection[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISection[]>) => response.body)
      )
      .subscribe((res: ISection[]) => (this.sections = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(coursePage: ICoursePage) {
    this.editForm.patchValue({
      id: coursePage.id,
      rating: coursePage.rating,
      sections: coursePage.sections
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const coursePage = this.createFromForm();
    if (coursePage.id !== undefined) {
      this.subscribeToSaveResponse(this.coursePageService.update(coursePage));
    } else {
      this.subscribeToSaveResponse(this.coursePageService.create(coursePage));
    }
  }

  private createFromForm(): ICoursePage {
    return {
      ...new CoursePage(),
      id: this.editForm.get(['id']).value,
      rating: this.editForm.get(['rating']).value,
      sections: this.editForm.get(['sections']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICoursePage>>) {
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
