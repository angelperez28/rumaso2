import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Rumaso2SharedModule } from 'app/shared/shared.module';
import { CoursePageComponent } from './course-page.component';
import { CoursePageDetailComponent } from './course-page-detail.component';
import { CoursePageUpdateComponent } from './course-page-update.component';
import { CoursePageDeletePopupComponent, CoursePageDeleteDialogComponent } from './course-page-delete-dialog.component';
import { coursePageRoute, coursePagePopupRoute } from './course-page.route';

const ENTITY_STATES = [...coursePageRoute, ...coursePagePopupRoute];

@NgModule({
  imports: [Rumaso2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CoursePageComponent,
    CoursePageDetailComponent,
    CoursePageUpdateComponent,
    CoursePageDeleteDialogComponent,
    CoursePageDeletePopupComponent
  ],
  entryComponents: [CoursePageDeleteDialogComponent]
})
export class Rumaso2CoursePageModule {}
