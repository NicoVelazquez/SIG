import {Component, OnInit} from '@angular/core';
import {HttpRequestsService} from '../../shared/services/http-requests.service';
import {Router} from '@angular/router';
import {AuthService} from '../../shared/services/auth.service';

@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.css']
})
export class ManagerComponent implements OnInit {

  tab = 'all';
  headers: string[] = ['ID', 'Cliente', 'Dia', 'Estado Tracking'];
  applications = [];
  filteredApplications = [];

  clientSearch: string;

  constructor(private rs: HttpRequestsService, private router: Router, private authService: AuthService) {
  }

  ngOnInit() {
    this.rs.getManagerApplications().then(data => {
      this.applications = data;
      this.filteredApplications = data;
      if (!this.authService.isManager()) {
        this.switchTab('warehouse');
      }

      this.checkAcceptedApplications();
    });
  }

  checkAcceptedApplications() {
    this.applications.forEach(e => {
      if (e.state.toLowerCase() === 'aceptada') {
        setTimeout(() => {
          e.state = 'En Camino';
          e.operator_acceptance_date = new Date();
          this.rs.updateApplication(e);
          this.checkAcceptedApplications();
        }, 10000);
      }

      if (e.state.toLowerCase() === 'en camino') {
        setTimeout(() => {
          e.state = 'Recolectada';
          e.collectionDate = new Date();
          this.rs.updateApplication(e);
          this.checkAcceptedApplications();
        }, 10000);
      }

      if (e.state.toLowerCase() === 'recolectada') {
        setTimeout(() => {
          e.state = 'En Almacen';
          this.rs.updateApplication(e);
        }, 10000);
      }
    });
  }

  filterApplications() {
    switch (this.tab) {
      case 'all': {
        this.filteredApplications = this.applications.filter(e =>
          e.client.toLowerCase().includes(this.clientSearch.toLowerCase())
        );
        break;
      }
      case 'new': {
        this.filteredApplications = this.applications.filter(e =>
          e.client.toLowerCase().includes(this.clientSearch.toLowerCase()) && e.state.toLowerCase() === 'nueva'
        );
        break;
      }
      case 'warehouse': {
        this.filteredApplications = this.applications.filter(e =>
          e.client.toLowerCase().includes(this.clientSearch.toLowerCase()) && e.state.toLowerCase() === 'en almacen'
        );
        break;
      }
      case 'controlled': {
        this.filteredApplications = this.applications.filter(e =>
          e.client.toLowerCase().includes(this.clientSearch.toLowerCase()) && e.state.toLowerCase() === 'controlada'
        );
        break;
      }
      case 'note': {
        this.filteredApplications = this.applications.filter(e =>
          e.client.toLowerCase().includes(this.clientSearch.toLowerCase()) && e.state.toLowerCase() === 'con nota'
        );
        break;
      }
      default: {
        console.log('Hay estados mal');
      }
    }
  }

  switchTab(state: string) {
    this.clientSearch = '';
    this.tab = state.toLowerCase();
    this.filterApplications();
  }

  reviewApplication(application: any) {
    if (application.state.toLowerCase() === 'en almacen') {
      this.router.navigate(['/review-application/' + application.id], {queryParams: {state: 'en almacen'}});
    } else if (application.state.toLowerCase() === 'nueva') {
      this.router.navigate(['/review-application/' + application.id], {queryParams: {state: 'nueva'}});
    } else if (application.state.toLowerCase() === 'rechazada') {
      this.router.navigate(['/review-application/' + application.id], {queryParams: {state: 'rechazada'}});
    } else if (application.state.toLowerCase() === 'controlada') {
      this.router.navigate(['/review-application/' + application.id], {queryParams: {state: 'controlada'}});
    } else if (application.state.toLowerCase() === 'con nota') {
      this.router.navigate(['/review-note/' + application.id], {queryParams: {state: 'con nota'}});
    } else {
      this.router.navigate(['/review-application/' + application.id]);
    }
  }

  generateNote(id: any) {
    this.router.navigate(['/create-note/' + id]);
  }
}
