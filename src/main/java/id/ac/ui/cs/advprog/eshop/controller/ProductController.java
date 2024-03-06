package id.ac.ui.cs.advprog.eshop.controller;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model){
        service.create(product);
        return "redirect:list";
    }
//    @GetMapping("/delete/{id}")
//    public String deleteProduct(@PathVariable("id") String id) {
//        service.delete(id);
//        return "redirect:/product/list";
//    }

    @GetMapping("/list")
    public String productListPage(Model model){
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }
    @GetMapping("")
    public String showHomePage() {
        return "HomePage"; // Return the name of the HTML file without the extension
    }

    //Method-method baru untuk edit dan delete produk

    @GetMapping("/edit/{id}")
    public String editProductPage(@PathVariable("id") String id, Model model) {
        Product product = service.getProduct(id); // Asumsikan metode ini mengembalikan produk berdasarkan ID
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/edit")
    public String editProductPost(@ModelAttribute Product product, Model model){
        service.update(product); // Langsung update produk
        return "redirect:list";
    }

    @PostMapping ("/delete")
    public String deleteProduct(@RequestParam("productId")String id){
        service.delete(id);
        return "redirect:list";
    }
}

@Controller
@RequestMapping("/car")
class CarController extends ProductController{
    @Autowired
    private CarServiceImpl carservice;

    @GetMapping("/createCar")
    public String createCarPage(Model model) {
        Car car = new Car();
        model.addAttribute ("car", car);
        return "createCar";
    }

    @PostMapping("/createCar")
    public String createCarpost(@ModelAttribute Car car, Model model){
        carservice.create(car);
        return "redirect:listCar";
    }
    @GetMapping("/Listcar")
    public String carListPage(Model model){
        List<Car> allCars = carservice.findAll();
        model.addAttribute("cars", allCars);
        return "carList";
    }


    @GetMapping("/editcar/fcarId")
    public String editCarpage(@PathVariable String carId, Model model) {
        Car car = carservice. findById(carId);
        model.addAttribute( "car", car);
        return "editCar";
    }
    @PostMapping("/editcar")
    public String editCarpost(@ModelAttribute Car car, Model model) {
        System.out.println(car.getCarId());
        carservice.update(car.getCarId(), car);
        return "redirect:ListCar";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") String carId) {
        carservice.deleteCarById(carId);
        return "redirect:ListCar";
    }
}



