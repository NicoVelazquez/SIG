import {Component, OnInit} from '@angular/core';
import {HttpRequestsService} from '../../shared/services/http-requests.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.css']
})
export class ManagerComponent implements OnInit {

  tab = 'all';

  // TODO - definir los headers de la tabla
  headers: string[] = ['ID', 'Cliente', 'Dia', 'Estado Tracking'];
  applications = [];
  filteredApplications = [];

  clientSearch: string;

  constructor(private rs: HttpRequestsService, private router: Router) {
  }

  ngOnInit() {
    // TODO - definir de donde voy a obtener el id del cliente (podria estar en el LocalStorage al hacer el login)
    this.rs.getManagerApplications().then(data => {
      this.applications = data;
      this.filteredApplications = data;
      console.log(this.filteredApplications);
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
      case 'controlled': {
        this.filteredApplications = this.applications.filter(e =>
          e.client.toLowerCase().includes(this.clientSearch.toLowerCase()) && e.state.toLowerCase() === 'controlada'
        );
        break;
      }
      case 'warehouse': {
        this.filteredApplications = this.applications.filter(e =>
          e.client.toLowerCase().includes(this.clientSearch.toLowerCase()) && e.state.toLowerCase() === 'en almacen'
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
    } else {
      this.router.navigate(['/review-application/' + application.id]);
    }
  }

  generateNote(id: any) {
    this.router.navigate(['/create-note/' + id]);
  }
}
