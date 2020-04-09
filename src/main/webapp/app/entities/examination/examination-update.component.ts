import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IExamination, Examination } from 'app/shared/model/examination.model';
import { ExaminationService } from './examination.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

type SelectableEntity = IUser | IPatient;

@Component({
  selector: 'jhi-examination-update',
  templateUrl: './examination-update.component.html'
})
export class ExaminationUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  patients: IPatient[] = [];

  editForm = this.fb.group({
    id: [],
    weight: [null, [Validators.required]],
    height: [null, [Validators.required]],
    diastolicPressure: [null, [Validators.required]],
    systolicPressure: [null, [Validators.required]],
    examinationDate: [],
    lastModifiedDate: [],
    createdDate: [],
    userId: [],
    patientId: [null, Validators.required]
  });

  constructor(
    protected examinationService: ExaminationService,
    protected userService: UserService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examination }) => {
      if (!examination.id) {
        const today = moment().startOf('day');
        examination.examinationDate = today;
        examination.lastModifiedDate = today;
        examination.createdDate = today;
      }

      this.updateForm(examination);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));
    });
  }

  updateForm(examination: IExamination): void {
    this.editForm.patchValue({
      id: examination.id,
      weight: examination.weight,
      height: examination.height,
      diastolicPressure: examination.diastolicPressure,
      systolicPressure: examination.systolicPressure,
      examinationDate: examination.examinationDate ? examination.examinationDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedDate: examination.lastModifiedDate ? examination.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      createdDate: examination.createdDate ? examination.createdDate.format(DATE_TIME_FORMAT) : null,
      userId: examination.userId,
      patientId: examination.patientId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const examination = this.createFromForm();
    if (examination.id !== undefined) {
      this.subscribeToSaveResponse(this.examinationService.update(examination));
    } else {
      this.subscribeToSaveResponse(this.examinationService.create(examination));
    }
  }

  private createFromForm(): IExamination {
    return {
      ...new Examination(),
      id: this.editForm.get(['id'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      height: this.editForm.get(['height'])!.value,
      diastolicPressure: this.editForm.get(['diastolicPressure'])!.value,
      systolicPressure: this.editForm.get(['systolicPressure'])!.value,
      examinationDate: this.editForm.get(['examinationDate'])!.value
        ? moment(this.editForm.get(['examinationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userId: this.editForm.get(['userId'])!.value,
      patientId: this.editForm.get(['patientId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamination>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
