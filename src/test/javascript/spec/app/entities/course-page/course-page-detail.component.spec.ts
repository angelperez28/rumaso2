import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Rumaso2TestModule } from '../../../test.module';
import { CoursePageDetailComponent } from 'app/entities/course-page/course-page-detail.component';
import { CoursePage } from 'app/shared/model/course-page.model';

describe('Component Tests', () => {
  describe('CoursePage Management Detail Component', () => {
    let comp: CoursePageDetailComponent;
    let fixture: ComponentFixture<CoursePageDetailComponent>;
    const route = ({ data: of({ coursePage: new CoursePage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Rumaso2TestModule],
        declarations: [CoursePageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CoursePageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CoursePageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.coursePage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
