import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Rumaso2SharedModule } from 'app/shared/shared.module';
import { SectionComponent } from './section.component';
import { SectionDetailComponent } from './section-detail.component';
import { SectionUpdateComponent } from './section-update.component';
import { SectionDeletePopupComponent, SectionDeleteDialogComponent } from './section-delete-dialog.component';
import { sectionRoute, sectionPopupRoute } from './section.route';

const ENTITY_STATES = [...sectionRoute, ...sectionPopupRoute];

@NgModule({
  imports: [Rumaso2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SectionComponent,
    SectionDetailComponent,
    SectionUpdateComponent,
    SectionDeleteDialogComponent,
    SectionDeletePopupComponent
  ],
  entryComponents: [SectionDeleteDialogComponent]
})
export class Rumaso2SectionModule {}
