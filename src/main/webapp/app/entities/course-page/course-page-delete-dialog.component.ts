import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICoursePage } from 'app/shared/model/course-page.model';
import { CoursePageService } from './course-page.service';

@Component({
  selector: 'jhi-course-page-delete-dialog',
  templateUrl: './course-page-delete-dialog.component.html'
})
export class CoursePageDeleteDialogComponent {
  coursePage: ICoursePage;

  constructor(
    protected coursePageService: CoursePageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.coursePageService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'coursePageListModification',
        content: 'Deleted an coursePage'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-course-page-delete-popup',
  template: ''
})
export class CoursePageDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ coursePage }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CoursePageDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.coursePage = coursePage;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/course-page', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/course-page', { outlets: { popup: null } }]);
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
