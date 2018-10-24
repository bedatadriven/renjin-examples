



doGet <- function(uri, queryParams) {

  if(uri == "/httr") {
    x <- httr::GET("https://httpstat.us/200")
    return(sprintf("status code = %d", x$status_code))
  }
  if(uri == "/home") {
    return(R.home())
  }

  if(is.null(queryParams$name)) {
    return("Hello World")
  } else {
    return(paste("Hello", queryParams$name))
  }

}