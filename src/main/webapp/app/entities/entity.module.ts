import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'patient',
        loadChildren: () => import('./patient/patient.module').then(m => m.HipsterAcademyPatientModule)
      },
      {
        path: 'examination',
        loadChildren: () => import('./examination/examination.module').then(m => m.HipsterAcademyExaminationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class HipsterAcademyEntityModule {}
