import React, { useRef } from "react";
import ProductList from "./components/ProductList";
import ProductForm from "./components/ProductForm";

function App() {
  const productListRef = useRef();

  const refreshList = () => {
    if (productListRef.current) {
      productListRef.current.fetchProducts();
    }
  };

  return (
    <div className="App">
      <h1>Product Management</h1>
      {/* //{ 👇 Admin-only } */}
      <ProductForm onSave={refreshList} />
      {/* // 👇 Everyone can view * */}
      <ProductList ref={productListRef} />
    </div>
  );
}

export default App;
