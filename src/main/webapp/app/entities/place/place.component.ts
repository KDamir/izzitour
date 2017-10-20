import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Place } from './place.model';
import { PlaceService } from './place.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-place',
    templateUrl: './place.component.html'
})
export class PlaceComponent implements OnInit, OnDestroy {
places: Place[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private placeService: PlaceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.placeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.places = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlaces();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Place) {
        return item.id;
    }
    registerChangeInPlaces() {
        this.eventSubscriber = this.eventManager.subscribe('placeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
