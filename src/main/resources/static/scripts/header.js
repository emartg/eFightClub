var header = document.getElementById('Header')

window.addEventListener('scroll',()=>{
    var scroll = window.scrollY
    if(scroll>1){        
        header.style.position = 'fixed'
    }else{
        header.style.position = 'static'
    }
})