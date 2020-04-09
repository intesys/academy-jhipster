import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExamination } from 'app/shared/model/examination.model';
import { ExaminationService } from './examination.service';

@Component({
  templateUrl: './examination-delete-dialog.component.html'
})
export class ExaminationDeleteDialogComponent {
  examination?: IExamination;

  constructor(
    protected examinationService: ExaminationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.examinationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('examinationListModification');
      this.activeModal.close();
    });
  }
}
