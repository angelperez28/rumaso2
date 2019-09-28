import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Rumaso2TestModule } from '../../../test.module';
import { CoursePageComponent } from 'app/entities/course-page/course-page.component';
import { CoursePageService } from 'app/entities/course-page/course-page.service';
import { CoursePage } from 'app/shared/model/course-page.model';

describe('Component Tests', () => {
  describe('CoursePage Management Component', () => {
    let comp: CoursePageComponent;
    let fixture: ComponentFixture<CoursePageComponent>;
    let service: CoursePageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Rumaso2TestModule],
        declarations: [CoursePageComponent],
        providers: []
      })
        .overrideTemplate(CoursePageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CoursePageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CoursePageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CoursePage(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.coursePages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
