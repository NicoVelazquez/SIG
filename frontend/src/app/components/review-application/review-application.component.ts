import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpRequestsService} from '../../shared/services/http-requests.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-review-application',
  templateUrl: './review-application.component.html',
  styleUrls: ['./review-application.component.css']
})
export class ReviewApplicationComponent implements OnInit, OnDestroy {

  subscription: Subscription;
  application: any;
  headers = ['Nombre', 'Fecha', 'Lote', 'Cantidad'];
  state: string;
  userType = 'manager';

  observations = '';
  canFinishControlling = true;

  constructor(private rs: HttpRequestsService, private router: Router, private aRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.aRoute.queryParams.subscribe(params => {
      // TODO (NV) - Hace falta? O la application ya tiene state?
      if (params.state) {
        this.state = params.state;
      }
      if (params.user) {
        this.userType = params.user;
      }
    });

    this.subscription = this.aRoute.params.subscribe(params => {
      this.rs.getApplication(params.id).then(data => {
        this.application = data;
        if (this.application.state.toLowerCase() === 'en almacen') {
          this.application.products.forEach(e => {
            e.accepted = 0;
            e.good = 0;
          });
        }
      });
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  accept_denyApplication(action: string) {
    this.application.state = action;
    console.log(this.application);
    this.rs.updateApplication(this.application).then(() => {
      this.router.navigate(['home']);
    }).catch(err => {
      console.log('error en el update application');
      console.log(err);
    });
  }

  deleteApplication() {
    this.rs.deleteClientApplication(this.application).then(() => {
      this.router.navigate(['home']);
    });
  }

  finishControlling() {
    this.application.state = 'Controlada';
    this.application.observation = this.observations;
    console.log(this.application);
    this.rs.updateApplication(this.application).then(() => {
      this.router.navigate(['home']);
    });
  }

  validateAcceptedGoodQuantity($event: any, product: any) {
    this.canFinishControlling = (product.quantity >= product.accepted && product.accepted >= product.good && product.good >= 0);
  }
}
