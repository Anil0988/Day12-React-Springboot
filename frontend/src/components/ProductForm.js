import React, { useState } from "react";
import api from "../axios";

function ProductForm({ onSave }) {
  const [product, setProduct] = useState({ name: "", price: "", category: "" });

  const handleChange = (e) => {
    setProduct({ ...product, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post("/products/add", product); // 👈 requires ROLE_ADMIN
      alert("Product saved!");
      setProduct({ name: "", price: "", category: "" });
      onSave(); // refresh product list
    } catch (err) {
      alert("Error saving product: " + err.message);
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: "20px" }}>
      <input
        name="name"
        placeholder="Name"
        value={product.name}
        onChange={handleChange}
        required
      />
      <input
        name="price"
        type="number"
        placeholder="Price"
        value={product.price}
        onChange={handleChange}
        required
      />
      <input
        name="category"
        placeholder="Category"
        value={product.category}
        onChange={handleChange}
        required
      />
      <button type="submit">Save</button>
    </form>
  );
}

export default ProductForm;
