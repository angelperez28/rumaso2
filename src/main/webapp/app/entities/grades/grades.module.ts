import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Rumaso2SharedModule } from 'app/shared/shared.module';
import { GradesComponent } from './grades.component';
import { GradesDetailComponent } from './grades-detail.component';
import { GradesUpdateComponent } from './grades-update.component';
import { GradesDeletePopupComponent, GradesDeleteDialogComponent } from './grades-delete-dialog.component';
import { gradesRoute, gradesPopupRoute } from './grades.route';

const ENTITY_STATES = [...gradesRoute, ...gradesPopupRoute];

@NgModule({
  imports: [Rumaso2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [GradesComponent, GradesDetailComponent, GradesUpdateComponent, GradesDeleteDialogComponent, GradesDeletePopupComponent],
  entryComponents: [GradesDeleteDialogComponent]
})
export class Rumaso2GradesModule {}
