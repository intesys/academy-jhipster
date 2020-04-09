import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExamination } from 'app/shared/model/examination.model';

@Component({
  selector: 'jhi-examination-detail',
  templateUrl: './examination-detail.component.html'
})
export class ExaminationDetailComponent implements OnInit {
  examination: IExamination | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examination }) => (this.examination = examination));
  }

  previousState(): void {
    window.history.back();
  }
}
