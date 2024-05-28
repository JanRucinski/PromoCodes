# Task for LAT

## Context

Create an application to manage discount codes for sales or promotions (a.k.a promo codes).

## Functional requirements

1. A promo code is a text with 3-24 alphanumeric case-sensitive characters which must not contain whitespaces and must be unique.
2. Each promo code:
   1. should have an expiration date
   2. should have a discount amount and currency - the application should not be limited to only one currency
   3. should have a maximal number of allowed usages
   4. can be applied to any product
3. Logic for calculating the discount price:
   1. If promo code doesn't exist, return HTTP 404.
   2. If promo code expired, return the regular price with warning.
   3. If currencies doesn't match, return the regular price with warning.
   4. If the maximal number of usages was achieved, return the regular price with warning.
   5. Otherwise, calculate the discount price: `discount price = regular price - promo code discount`; e.g. `999 (regular price) - 100 (promo code discount) = 899 (discount price)`
   6. If discount price is below zero, return `0`.
   7. Any promo code can be applied to any product.
   8. Calculation of the discount does not mean that the code was used for purchase.
   9. [Optional] Implement a second type of promo code based on percentage. The discount price should be calculated as `discount price = regular price - (regular price * promo code discount)`; e.g. `100 (regular price) - 100 (regular price) * 15% (promo code discount) = 100 - 15 = 85 (discount price)`
4. Logic for simulating a purchase:
   1. Calculate the discount price (making sure that rules for calculating the discount price are not violated).
   2. Store in the database the information about the purchase:
      1. date of purchase
      2. what was the regular price
      3. the amount of discount applied if promo code was used
      4. what product was purchased (for simplicity we assume that one purchase = one product)
5. Each product:
   1. should have a required name and optional description
   2. should have a regular price (amount with currency)

## REST API endpoints

1. Create a new product
2. Get all products
3. Update product data
4. Create a new promo code.
5. Get all promo codes.
6. Get one promo code's details by providing the promo code. The detail should also contain the number of usages.
7. Get the discount price by providing a product and a promo code.
8. Simulate purchase
9. [Optional] A sales report: number of purchases and total value by currency (see below)

Sales report example:
| Currency | Total amount | Total discount | No of purchases |
|:--------:|-------------:|---------------:|----------------:|
| EUR      |      1024.16 |         256.00 |             128 |
| USD      |       512.32 |          64.00 |              32 |

## Non-functional requirements

1. Crete only the backend part (no UI is required).
2. The application should expose a REST API.
3. Use Java programming language and Spring framework.
4. Use Maven or Gradle.
5. Use relational in-memory database (e.g. H2).
6. No security features (authorization, authentication, encryption etc.) are required.

## Hints

1. Remember that correct operation of the application has a higher priority than completing all the functionality of the system. It is better to write less but well than more and with errors.
2. Think about unusual use cases and test your application.
3. Include a short instruction how to build and run the application with URL's to REST services along with sample queries.
4. Keep the database schema as simple as possible.
5. Remember about decimal points in amounts (e.g. `0.99 EUR`).
6. Try to commit regularly, so that you can trace the development of the application.

## Submission form

Please submit the result of your work as GIT repository.
