import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGrades } from 'app/shared/model/grades.model';
import { GradesService } from './grades.service';

@Component({
  selector: 'jhi-grades-delete-dialog',
  templateUrl: './grades-delete-dialog.component.html'
})
export class GradesDeleteDialogComponent {
  grades: IGrades;

  constructor(protected gradesService: GradesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.gradesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'gradesListModification',
        content: 'Deleted an grades'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-grades-delete-popup',
  template: ''
})
export class GradesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ grades }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(GradesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.grades = grades;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/grades', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/grades', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
