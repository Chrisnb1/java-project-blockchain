const express = require('express')
const app = express()
const path = require('path');
const port = 3000
const router = express.Router();

app.use(express.static('public'));
router.get('/', function(req, res){ 

    res.sendFile(path.join(__dirname+'/public/html/index.html'));
});


app.use('/',router);
app.listen(port, () => console.log(`Example app listening at http://localhost:${port}`))