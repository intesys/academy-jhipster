import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExamination } from 'app/shared/model/examination.model';

type EntityResponseType = HttpResponse<IExamination>;
type EntityArrayResponseType = HttpResponse<IExamination[]>;

@Injectable({ providedIn: 'root' })
export class ExaminationService {
  public resourceUrl = SERVER_API_URL + 'api/examinations';

  constructor(protected http: HttpClient) {}

  create(examination: IExamination): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(examination);
    return this.http
      .post<IExamination>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(examination: IExamination): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(examination);
    return this.http
      .put<IExamination>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExamination>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExamination[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(examination: IExamination): IExamination {
    const copy: IExamination = Object.assign({}, examination, {
      examinationDate:
        examination.examinationDate && examination.examinationDate.isValid() ? examination.examinationDate.toJSON() : undefined,
      lastModifiedDate:
        examination.lastModifiedDate && examination.lastModifiedDate.isValid() ? examination.lastModifiedDate.toJSON() : undefined,
      createdDate: examination.createdDate && examination.createdDate.isValid() ? examination.createdDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.examinationDate = res.body.examinationDate ? moment(res.body.examinationDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((examination: IExamination) => {
        examination.examinationDate = examination.examinationDate ? moment(examination.examinationDate) : undefined;
        examination.lastModifiedDate = examination.lastModifiedDate ? moment(examination.lastModifiedDate) : undefined;
        examination.createdDate = examination.createdDate ? moment(examination.createdDate) : undefined;
      });
    }
    return res;
  }
}
