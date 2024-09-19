// tableRoutes.js
const tableRoutes = [];
for (let i = 1; i <= 17; i++) {
    tableRoutes.push({
        path: `table${i}`,
        name: `Table${i}`,
        component: () => import(`../views/tableForm/Table${i}.vue`)
    });
}

for (let i = 51; i <= 54; i++) {
    tableRoutes.push({
        path: `table${i}`,
        name: `Table${i}`,
        component: () => import(`../views/tableForm/Table${i}.vue`)
    });
}

export default tableRoutes;
