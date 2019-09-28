import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Rumaso2TestModule } from '../../../test.module';
import { GradesDeleteDialogComponent } from 'app/entities/grades/grades-delete-dialog.component';
import { GradesService } from 'app/entities/grades/grades.service';

describe('Component Tests', () => {
  describe('Grades Management Delete Component', () => {
    let comp: GradesDeleteDialogComponent;
    let fixture: ComponentFixture<GradesDeleteDialogComponent>;
    let service: GradesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Rumaso2TestModule],
        declarations: [GradesDeleteDialogComponent]
      })
        .overrideTemplate(GradesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GradesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GradesService);
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
