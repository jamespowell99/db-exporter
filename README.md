https://zoom.us/j/298058880?pwd=TDFDWWVsUjF2bnNEUG00LzNXVnd6Zz09

**docker notes**

`docker run --rm   --name pg-docker -e POSTGRES_PASSWORD=docker -d -p 7432:5432 -v $HOME/docker/volumes/postgres:/var/lib/postgresql/data  postgres:10`

`docker stop pg-docker`

`rm -rf $HOME/docker/volumes/postgres`


**stats queries**
```
--order count by year
select extract(YEAR from "Date") the_year, count(*)  from dryhome.dp_orders group by the_year order by the_year;

--order totals
select dpo.order_id, dpo.order_number, dpo.company_id, dpo."Date", sum (dpoi.price * dpoi.qty) total, count(*)/*dpoi."Prod_id", dpoi.qty, dpoi.price, dpoi.price * dpoi.qty item_total*/
from dryhome.dp_orders dpo, dryhome.dp_order_items dpoi where dpoi.order_id = dpo.order_id
group by dpo.order_id, dpo.order_number, dpo.company_id, dpo."Date";

--order amount by year
select extract(YEAR from "Date") the_year, sum(total) from (
select dpo.order_id, dpo.order_number, dpo.company_id, dpo."Date", sum (dpoi.price * dpoi.qty) total, count(*)/*dpoi."Prod_id", dpoi.qty, dpoi.price, dpoi.price * dpoi.qty item_total*/
from dryhome.dp_orders dpo, dryhome.dp_order_items dpoi where dpoi.order_id = dpo.order_id
group by dpo.order_id, dpo.order_number, dpo.company_id, dpo."Date") order_totals group by the_year order by the_year;

--top 20 customers by order count
select company_id, dp."Company Name:", count(*) the_count
from dryhome.dp_orders dpo, dryhome.damp_proofers dp
  where dpo.company_id = dp."ID"
group by company_id, dp."Company Name:" order by the_count desc limit 20;

-- top 20 customers by order count (filtered by year)
select company_id, dp."Company Name:", count(*) the_count
from dryhome.dp_orders dpo, dryhome.damp_proofers dp
where dpo.company_id = dp."ID" and extract(YEAR from "Date") =2019
group by company_id, dp."Company Name:" order by the_count desc limit 20;


-- top 20 customers by order amount
select company_id, "Company Name:", sum(total) from (
 select dpo.order_id, dpo.order_number, dpo.company_id, dp."Company Name:",dpo."Date", sum (dpoi.price * dpoi.qty) total, count(*)/*dpoi."Prod_id", dpoi.qty, dpoi.price, dpoi.price * dpoi.qty item_total*/
 from dryhome.dp_orders dpo, dryhome.dp_order_items dpoi, dryhome.damp_proofers dp
 where dpoi.order_id = dpo.order_id and dp."ID" = dpo.company_id
 group by dpo.order_id, dpo.order_number, dpo.company_id, dp."Company Name:",dpo."Date") order_totals
GROUP BY company_id, "Company Name:" order by sum desc limit 20;

-- top 20 customers by order amount (filtered by year)
select company_id, "Company Name:", sum(total) from (
select dpo.order_id, dpo.order_number, dpo.company_id, dp."Company Name:",dpo."Date", sum (dpoi.price * dpoi.qty) total, count(*)/*dpoi."Prod_id", dpoi.qty, dpoi.price, dpoi.price * dpoi.qty item_total*/
from dryhome.dp_orders dpo, dryhome.dp_order_items dpoi, dryhome.damp_proofers dp
where dpoi.order_id = dpo.order_id and dp."ID" = dpo.company_id
      and extract(YEAR from "Date") =2019
group by dpo.order_id, dpo.order_number, dpo.company_id, dp."Company Name:",dpo."Date") order_totals
GROUP BY company_id, "Company Name:" order by sum desc limit 20;

--top towns (damp proofers)
select "Town", count(*) the_count
from dryhome.damp_proofers
GROUP BY "Town" order by the_count desc;


--top towns (domestics)
select "Town", count(*) the_count
from dryhome.domestics
GROUP BY "Town" order by the_count desc;

--product count
select p.prod_name, sum(qty) the_sum
from dryhome.dp_order_items dpoi, dryhome.products p
where dpoi."Prod_id" = p.prod_id group by prod_name order by the_sum desc;

--product count by year
select extract(YEAR from "Date"), p.prod_name, sum(qty) the_sum
from dryhome.dp_order_items dpoi, dryhome.products p, dryhome.dp_orders dpo
where dpoi."Prod_id" = p.prod_id and dpo.order_id = dpoi.order_id
group by extract(YEAR from "Date"), prod_name order by date_part desc;```
>>>>>>> Stashed changes
```

**data importing**

deleting
```
echo "delete from dryhomecrm.order_item; delete from dryhomecrm.customer_order; delete from dryhomecrm.product; delete from dryhomecrm.customer;" |  docker exec -i pg-docker psql --u postgres  dryhomecrm
```

counting
```
echo "select count(*) from dryhomecrm.customer; select count(*) from dryhomecrm.product; select count(*) from dryhomecrm.customer_order; select count(*) from dryhomecrm.order_item" |  docker exec -i pg-docker psql --u postgres  dryhomecrm
```

importing
```
 cat data/* |  docker exec -i pg-docker psql --u postgres  dryhomecrm
```
