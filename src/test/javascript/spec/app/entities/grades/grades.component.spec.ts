import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Rumaso2TestModule } from '../../../test.module';
import { GradesComponent } from 'app/entities/grades/grades.component';
import { GradesService } from 'app/entities/grades/grades.service';
import { Grades } from 'app/shared/model/grades.model';

describe('Component Tests', () => {
  describe('Grades Management Component', () => {
    let comp: GradesComponent;
    let fixture: ComponentFixture<GradesComponent>;
    let service: GradesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Rumaso2TestModule],
        declarations: [GradesComponent],
        providers: []
      })
        .overrideTemplate(GradesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GradesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GradesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Grades(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.grades[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
