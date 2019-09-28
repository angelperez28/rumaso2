import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'section',
        loadChildren: () => import('./section/section.module').then(m => m.Rumaso2SectionModule)
      },
      {
        path: 'course-page',
        loadChildren: () => import('./course-page/course-page.module').then(m => m.Rumaso2CoursePageModule)
      },
      {
        path: 'professor',
        loadChildren: () => import('./professor/professor.module').then(m => m.Rumaso2ProfessorModule)
      },
      {
        path: 'grades',
        loadChildren: () => import('./grades/grades.module').then(m => m.Rumaso2GradesModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class Rumaso2EntityModule {}
