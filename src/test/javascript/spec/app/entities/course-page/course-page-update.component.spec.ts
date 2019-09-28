import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Rumaso2TestModule } from '../../../test.module';
import { CoursePageUpdateComponent } from 'app/entities/course-page/course-page-update.component';
import { CoursePageService } from 'app/entities/course-page/course-page.service';
import { CoursePage } from 'app/shared/model/course-page.model';

describe('Component Tests', () => {
  describe('CoursePage Management Update Component', () => {
    let comp: CoursePageUpdateComponent;
    let fixture: ComponentFixture<CoursePageUpdateComponent>;
    let service: CoursePageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Rumaso2TestModule],
        declarations: [CoursePageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CoursePageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CoursePageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CoursePageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CoursePage(123);
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
        const entity = new CoursePage();
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
