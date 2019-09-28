import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICoursePage } from 'app/shared/model/course-page.model';
import { AccountService } from 'app/core/auth/account.service';
import { CoursePageService } from './course-page.service';

@Component({
  selector: 'jhi-course-page',
  templateUrl: './course-page.component.html'
})
export class CoursePageComponent implements OnInit, OnDestroy {
  coursePages: ICoursePage[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected coursePageService: CoursePageService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.coursePageService
      .query()
      .pipe(
        filter((res: HttpResponse<ICoursePage[]>) => res.ok),
        map((res: HttpResponse<ICoursePage[]>) => res.body)
      )
      .subscribe(
        (res: ICoursePage[]) => {
          this.coursePages = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCoursePages();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICoursePage) {
    return item.id;
  }

  registerChangeInCoursePages() {
    this.eventSubscriber = this.eventManager.subscribe('coursePageListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
