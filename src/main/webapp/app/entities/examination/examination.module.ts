import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HipsterAcademySharedModule } from 'app/shared/shared.module';
import { ExaminationComponent } from './examination.component';
import { ExaminationDetailComponent } from './examination-detail.component';
import { ExaminationUpdateComponent } from './examination-update.component';
import { ExaminationDeleteDialogComponent } from './examination-delete-dialog.component';
import { examinationRoute } from './examination.route';

@NgModule({
  imports: [HipsterAcademySharedModule, RouterModule.forChild(examinationRoute)],
  declarations: [ExaminationComponent, ExaminationDetailComponent, ExaminationUpdateComponent, ExaminationDeleteDialogComponent],
  entryComponents: [ExaminationDeleteDialogComponent]
})
export class HipsterAcademyExaminationModule {}
