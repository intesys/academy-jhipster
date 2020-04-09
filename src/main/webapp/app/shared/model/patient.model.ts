import { Moment } from 'moment';
import { IExamination } from 'app/shared/model/examination.model';

export interface IPatient {
  id?: number;
  firstName?: string;
  lastName?: string;
  fiscalCode?: string;
  birthDate?: Moment;
  lastModifiedDate?: Moment;
  createdDate?: Moment;
  examinations?: IExamination[];
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public fiscalCode?: string,
    public birthDate?: Moment,
    public lastModifiedDate?: Moment,
    public createdDate?: Moment,
    public examinations?: IExamination[]
  ) {}
}
