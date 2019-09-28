import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Rumaso2TestModule } from '../../../test.module';
import { GradesUpdateComponent } from 'app/entities/grades/grades-update.component';
import { GradesService } from 'app/entities/grades/grades.service';
import { Grades } from 'app/shared/model/grades.model';

describe('Component Tests', () => {
  describe('Grades Management Update Component', () => {
    let comp: GradesUpdateComponent;
    let fixture: ComponentFixture<GradesUpdateComponent>;
    let service: GradesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Rumaso2TestModule],
        declarations: [GradesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GradesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GradesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GradesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Grades(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Grades();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
