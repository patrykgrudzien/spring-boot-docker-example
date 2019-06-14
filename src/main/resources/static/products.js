let products = [];

function findProduct (productId) {
    return products[findProductKey(productId)];
}

function findProductKey (productId) {
    for (let key = 0; key < products.length; key++) {
        if (products[key].id === productId) {
            return key;
        }
    }
}

const productService = {
    findAll(fun) {
        axios.get('/api/v1/products')
            .then(response => fun(response))
            .catch(error => console.log(error))
    },

    findById(id, fun) {
        axios.get('/api/v1/products/' + id)
            .then(response => fun(response))
            .catch(error => console.log(error))
    },

    create(product, fun) {
        axios.post('/api/v1/products', product)
            .then(response => fun(response))
            .catch(error => console.log(error))
    },

    update(id, product, fun) {
        axios.put('/api/v1/products/' + id, product)
            .then(response => fun(response))
            .catch(error => console.log(error))
    },

    deleteProduct(id, fun) {
        axios.delete('/api/v1/products/' + id)
            .then(response => fun(response))
            .catch(error => console.log(error))
    }
};

const List = Vue.extend({
    template: '#product-list',
    data: function () {
        return {
            products: [],
            searchKey: ''
        };
    },
    computed: {
        filteredProducts() {
            return this.products.filter((product) => {
                return product.name.indexOf(this.searchKey) > -1
                    || product.description.indexOf(this.searchKey) > -1
                    || product.price.toString().indexOf(this.searchKey) > -1
            })
        }
    },
    mounted() {
        productService.findAll(response => {
            this.products = response.data;
            products = response.data
        })
    }
});

const Product = Vue.extend({
    template: '#product',
    data: function () {
        return {
            product: findProduct(this.$route.params.product_id)
        };
    }
});

const ProductEdit = Vue.extend({
    template: '#product-edit',
    data: function () {
        return {
            product: findProduct(this.$route.params.product_id)
        };
    },
    methods: {
        updateProduct: function () {
            productService.update(this.product.id, this.product, response => router.push('/'))
        }
    }
});

const ProductDelete = Vue.extend({
    template: '#product-delete',
    data: function () {
        return {
            product: findProduct(this.$route.params.product_id)
        };
    },
    methods: {
        deleteProduct: function () {
            productService.deleteProduct(this.product.id, response => router.push('/'))
        }
    }
});

const AddProduct = Vue.extend({
    template: '#add-product',
    data() {
        return {
            product: {
                name: '', description: '', price: 0
            }
        }
    },
    methods: {
        createProduct() {
            productService.create(this.product, response => router.push('/'))
        }
    }
});

const router = new VueRouter({
    routes: [
        {path: '/', component: List},
        {path: '/product/:product_id', component: Product, name: 'product'},
        {path: '/add-product', component: AddProduct},
        {path: '/product/:product_id/edit', component: ProductEdit, name: 'product-edit'},
        {path: '/product/:product_id/delete', component: ProductDelete, name: 'product-delete'}
    ]
});

new Vue({
    router
}).$mount('#app');