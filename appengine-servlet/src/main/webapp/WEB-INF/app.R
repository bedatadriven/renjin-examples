



doGet <- function(uri, queryParams) {

  if(is.null(queryParams$name)) {
    return("Hello World")
  } else {
    return(paste("Hello", queryParams$name))
  }

}