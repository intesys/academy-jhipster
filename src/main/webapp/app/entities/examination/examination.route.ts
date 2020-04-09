import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExamination, Examination } from 'app/shared/model/examination.model';
import { ExaminationService } from './examination.service';
import { ExaminationComponent } from './examination.component';
import { ExaminationDetailComponent } from './examination-detail.component';
import { ExaminationUpdateComponent } from './examination-update.component';

@Injectable({ providedIn: 'root' })
export class ExaminationResolve implements Resolve<IExamination> {
  constructor(private service: ExaminationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExamination> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((examination: HttpResponse<Examination>) => {
          if (examination.body) {
            return of(examination.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Examination());
  }
}

export const examinationRoute: Routes = [
  {
    path: '',
    component: ExaminationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Examinations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExaminationDetailComponent,
    resolve: {
      examination: ExaminationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Examinations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExaminationUpdateComponent,
    resolve: {
      examination: ExaminationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Examinations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExaminationUpdateComponent,
    resolve: {
      examination: ExaminationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Examinations'
    },
    canActivate: [UserRouteAccessService]
  }
];
