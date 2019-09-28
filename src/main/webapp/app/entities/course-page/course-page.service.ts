import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICoursePage } from 'app/shared/model/course-page.model';

type EntityResponseType = HttpResponse<ICoursePage>;
type EntityArrayResponseType = HttpResponse<ICoursePage[]>;

@Injectable({ providedIn: 'root' })
export class CoursePageService {
  public resourceUrl = SERVER_API_URL + 'api/course-pages';

  constructor(protected http: HttpClient) {}

  create(coursePage: ICoursePage): Observable<EntityResponseType> {
    return this.http.post<ICoursePage>(this.resourceUrl, coursePage, { observe: 'response' });
  }

  update(coursePage: ICoursePage): Observable<EntityResponseType> {
    return this.http.put<ICoursePage>(this.resourceUrl, coursePage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICoursePage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICoursePage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
