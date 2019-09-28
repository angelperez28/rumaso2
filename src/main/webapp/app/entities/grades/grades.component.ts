import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGrades } from 'app/shared/model/grades.model';
import { AccountService } from 'app/core/auth/account.service';
import { GradesService } from './grades.service';

@Component({
  selector: 'jhi-grades',
  templateUrl: './grades.component.html'
})
export class GradesComponent implements OnInit, OnDestroy {
  grades: IGrades[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected gradesService: GradesService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.gradesService
      .query()
      .pipe(
        filter((res: HttpResponse<IGrades[]>) => res.ok),
        map((res: HttpResponse<IGrades[]>) => res.body)
      )
      .subscribe(
        (res: IGrades[]) => {
          this.grades = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInGrades();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGrades) {
    return item.id;
  }

  registerChangeInGrades() {
    this.eventSubscriber = this.eventManager.subscribe('gradesListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
