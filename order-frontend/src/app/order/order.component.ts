import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { OrderService } from '../services/order.service';

@Component({
  selector: 'app-order',
  imports: [FormsModule],
  templateUrl: './order.component.html',
  styleUrl: './order.component.css'
})
export class OrderComponent { 
  productName: string = '';
  quantity: number = 0;
  message: string = '';
  price: number = 0;

  constructor(private orderService: OrderService) { }

  placeOrder(){
    const order = {
      productName: this.productName,
      quantity: this.quantity,
      price: this.price
    };

    this.orderService.placeOrder(order).subscribe({
      next: (response: any) => {
        this.message = 'Order placed succesfully! Status: ' + response.orderStatus;
      },
      error: (err) => {
        this.message = 'Order failed: ' + err.message;
      }

    });

  }

}
