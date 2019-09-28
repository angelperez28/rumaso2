import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Rumaso2TestModule } from '../../../test.module';
import { GradesDetailComponent } from 'app/entities/grades/grades-detail.component';
import { Grades } from 'app/shared/model/grades.model';

describe('Component Tests', () => {
  describe('Grades Management Detail Component', () => {
    let comp: GradesDetailComponent;
    let fixture: ComponentFixture<GradesDetailComponent>;
    const route = ({ data: of({ grades: new Grades(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Rumaso2TestModule],
        declarations: [GradesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GradesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GradesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.grades).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
