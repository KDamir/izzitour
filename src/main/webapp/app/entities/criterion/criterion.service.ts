import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Criterion } from './criterion.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CriterionService {

    private resourceUrl = SERVER_API_URL + 'api/criteria';

    constructor(private http: Http) { }

    create(criterion: Criterion): Observable<Criterion> {
        const copy = this.convert(criterion);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(criterion: Criterion): Observable<Criterion> {
        const copy = this.convert(criterion);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Criterion> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Criterion.
     */
    private convertItemFromServer(json: any): Criterion {
        const entity: Criterion = Object.assign(new Criterion(), json);
        return entity;
    }

    /**
     * Convert a Criterion to a JSON which can be sent to the server.
     */
    private convert(criterion: Criterion): Criterion {
        const copy: Criterion = Object.assign({}, criterion);
        return copy;
    }
}
