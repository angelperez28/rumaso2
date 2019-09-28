import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProfessor } from 'app/shared/model/professor.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProfessorService } from './professor.service';

@Component({
  selector: 'jhi-professor',
  templateUrl: './professor.component.html'
})
export class ProfessorComponent implements OnInit, OnDestroy {
  professors: IProfessor[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected professorService: ProfessorService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.professorService
      .query()
      .pipe(
        filter((res: HttpResponse<IProfessor[]>) => res.ok),
        map((res: HttpResponse<IProfessor[]>) => res.body)
      )
      .subscribe(
        (res: IProfessor[]) => {
          this.professors = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProfessors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProfessor) {
    return item.id;
  }

  registerChangeInProfessors() {
    this.eventSubscriber = this.eventManager.subscribe('professorListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
