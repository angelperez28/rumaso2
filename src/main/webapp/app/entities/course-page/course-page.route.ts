import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CoursePage } from 'app/shared/model/course-page.model';
import { CoursePageService } from './course-page.service';
import { CoursePageComponent } from './course-page.component';
import { CoursePageDetailComponent } from './course-page-detail.component';
import { CoursePageUpdateComponent } from './course-page-update.component';
import { CoursePageDeletePopupComponent } from './course-page-delete-dialog.component';
import { ICoursePage } from 'app/shared/model/course-page.model';

@Injectable({ providedIn: 'root' })
export class CoursePageResolve implements Resolve<ICoursePage> {
  constructor(private service: CoursePageService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICoursePage> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CoursePage>) => response.ok),
        map((coursePage: HttpResponse<CoursePage>) => coursePage.body)
      );
    }
    return of(new CoursePage());
  }
}

export const coursePageRoute: Routes = [
  {
    path: '',
    component: CoursePageComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CoursePages'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CoursePageDetailComponent,
    resolve: {
      coursePage: CoursePageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CoursePages'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CoursePageUpdateComponent,
    resolve: {
      coursePage: CoursePageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CoursePages'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CoursePageUpdateComponent,
    resolve: {
      coursePage: CoursePageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CoursePages'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const coursePagePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CoursePageDeletePopupComponent,
    resolve: {
      coursePage: CoursePageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CoursePages'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
