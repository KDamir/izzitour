import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Place } from './place.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PlaceService {

    private resourceUrl = SERVER_API_URL + 'api/places';

    constructor(private http: Http) { }

    create(place: Place): Observable<Place> {
        const copy = this.convert(place);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(place: Place): Observable<Place> {
        const copy = this.convert(place);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Place> {
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
     * Convert a returned JSON object to Place.
     */
    private convertItemFromServer(json: any): Place {
        const entity: Place = Object.assign(new Place(), json);
        return entity;
    }

    /**
     * Convert a Place to a JSON which can be sent to the server.
     */
    private convert(place: Place): Place {
        const copy: Place = Object.assign({}, place);
        return copy;
    }
}
