import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Rumaso2TestModule } from '../../../test.module';
import { CoursePageDeleteDialogComponent } from 'app/entities/course-page/course-page-delete-dialog.component';
import { CoursePageService } from 'app/entities/course-page/course-page.service';

describe('Component Tests', () => {
  describe('CoursePage Management Delete Component', () => {
    let comp: CoursePageDeleteDialogComponent;
    let fixture: ComponentFixture<CoursePageDeleteDialogComponent>;
    let service: CoursePageService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Rumaso2TestModule],
        declarations: [CoursePageDeleteDialogComponent]
      })
        .overrideTemplate(CoursePageDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CoursePageDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CoursePageService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
