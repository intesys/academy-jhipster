import { Moment } from 'moment';

export interface IExamination {
  id?: number;
  weight?: number;
  height?: number;
  diastolicPressure?: number;
  systolicPressure?: number;
  examinationDate?: Moment;
  lastModifiedDate?: Moment;
  createdDate?: Moment;
  userUserName?: string;
  userId?: number;
  patientLastName?: string;
  patientId?: number;
}

export class Examination implements IExamination {
  constructor(
    public id?: number,
    public weight?: number,
    public height?: number,
    public diastolicPressure?: number,
    public systolicPressure?: number,
    public examinationDate?: Moment,
    public lastModifiedDate?: Moment,
    public createdDate?: Moment,
    public userUserName?: string,
    public userId?: number,
    public patientLastName?: string,
    public patientId?: number
  ) {}
}
