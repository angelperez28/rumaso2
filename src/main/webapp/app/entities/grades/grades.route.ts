import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Grades } from 'app/shared/model/grades.model';
import { GradesService } from './grades.service';
import { GradesComponent } from './grades.component';
import { GradesDetailComponent } from './grades-detail.component';
import { GradesUpdateComponent } from './grades-update.component';
import { GradesDeletePopupComponent } from './grades-delete-dialog.component';
import { IGrades } from 'app/shared/model/grades.model';

@Injectable({ providedIn: 'root' })
export class GradesResolve implements Resolve<IGrades> {
  constructor(private service: GradesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IGrades> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Grades>) => response.ok),
        map((grades: HttpResponse<Grades>) => grades.body)
      );
    }
    return of(new Grades());
  }
}

export const gradesRoute: Routes = [
  {
    path: '',
    component: GradesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Grades'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GradesDetailComponent,
    resolve: {
      grades: GradesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Grades'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GradesUpdateComponent,
    resolve: {
      grades: GradesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Grades'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GradesUpdateComponent,
    resolve: {
      grades: GradesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Grades'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const gradesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: GradesDeletePopupComponent,
    resolve: {
      grades: GradesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Grades'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
