import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Answer } from './answer.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AnswerService {

    private resourceUrl = SERVER_API_URL + 'api/answers';

    constructor(private http: Http) { }

    create(answer: Answer): Observable<Answer> {
        const copy = this.convert(answer);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(answer: Answer): Observable<Answer> {
        const copy = this.convert(answer);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Answer> {
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
     * Convert a returned JSON object to Answer.
     */
    private convertItemFromServer(json: any): Answer {
        const entity: Answer = Object.assign(new Answer(), json);
        return entity;
    }

    /**
     * Convert a Answer to a JSON which can be sent to the server.
     */
    private convert(answer: Answer): Answer {
        const copy: Answer = Object.assign({}, answer);
        return copy;
    }
}
