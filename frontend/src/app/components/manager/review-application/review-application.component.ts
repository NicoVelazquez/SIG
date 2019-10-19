import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpRequestsService} from '../../../shared/services/http-requests.service';
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

  constructor(private rs: HttpRequestsService, private router: Router, private aRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.subscription = this.aRoute.params.subscribe(params => {
      this.rs.getManagerApplication(params.id).then(data => {
        this.application = data;
        this.application.products.map(e => e.state = 'unchecked');
        console.log(this.application);
      });
    });

    this.aRoute.queryParams.subscribe(params => {
      if (params.state) {
        this.state = params.state;
      }
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  hasFinishedControlling(): boolean {
    if (this.application) {
      this.application.products.forEach(e => {
        if (e.state === 'unchecked') {
          return true;
        }
      });
      return false;
    }
  }

  finishControlling() {
    this.application.state = 'Controlada';
    this.rs.controlledManagerApplication(this.application).then(() => {
      this.router.navigate(['home']);
    });
  }

  setProductState(p: any, state: string) {
    this.application.products.forEach(e => {
      if (e.name === p.name) {
        p.state = state;
      }
    });

    if (state === 'good') {
      document.getElementById(p.name + '-' + 'good').style.color = 'blue';
      document.getElementById(p.name + '-' + 'bad').style.color = 'grey';
    } else {
      document.getElementById(p.name + '-' + 'good').style.color = 'grey';
      document.getElementById(p.name + '-' + 'bad').style.color = 'red';
    }
  }

  accept_denyApplication(action: string) {
    if (action === 'accept') {
      this.rs.acceptManagerApplication(this.application.id).then(() => {
        this.router.navigate(['home']);
      });
    } else {
      this.rs.denyManagerApplication(this.application.id).then(() => {
        this.router.navigate(['home']);
      });
    }
  }
}
