insert into TL_NET_TEA_MONTH  

select to_date('2011-12','yyyy-mm') year_month,
    nvl(sum(nr.use_time),0) USE_TIME,
    nvl(sum(nr.use_flow),0) USE_FLOW,
    nvl(sum(nr.use_money),0) USE_MONEY,
    count(nr.id) ALL_COUNTS,
       t.tea_no,
       t.name_ tea_name,
       t.sex_code,
       tc.name_ sex_name ,
       t.dept_id,
       cd.name_ dept_name,
       t.edu_id,
       ce.name_ edu_name, --学历
       nr.on_type ON_TYPE_CODE,
       ntcode.name_ ON_TYPE_NAME --登陆方式
from t_net_record nr 
inner join t_net_user nu on nr.net_id=nu.id 
inner join t_tea t on nu.people_id=t.tea_no
left join t_code_dept_teach cd on t.dept_id =cd.id     
left join t_code_education ce on t.edu_id=ce.id  
left join t_code tc on t.sex_code=tc.code_ and tc.code_type='SEX_CODE'  
left join t_code ntcode on nr.on_type = ntcode.code_ and ntcode.code_type = 'NET_TYPE_CODE'
where  substr(nr.on_time, 0, 7)='2011-12' 
group by substr(nr.on_time, 0, 7),
       t.tea_no,
       t.name_,
       t.sex_code,
       tc.name_ ,
       t.dept_id,
       cd.name_,
       t.edu_id,
       ce.name_ ,
       nr.on_type ,
       ntcode.name_  
;

drop table TL_NET_TEA_MONTH;
create table TL_NET_TEA_MONTH
(
  YEAR_MONTH   DATE,
  USE_TIME     VARCHAR2(100),
  USE_FLOW     VARCHAR2(100),
  USE_MONEY    VARCHAR2(100),
  ALL_COUNTS   VARCHAR2(100),
  TEA_NO       VARCHAR2(100),
  TEA_NAME     VARCHAR2(100),
  SEX_CODE     VARCHAR2(100),
  SEX_NAME     VARCHAR2(100),
  DEPT_ID      VARCHAR2(100),
  DEPT_NAME    VARCHAR2(100),
  EDU_ID       VARCHAR2(100),
  EDU_NAME     VARCHAR2(100),
  ON_TYPE_CODE VARCHAR2(100),
  ON_TYPE_NAME VARCHAR2(100)
) partition by range (year_month)
(
partition part_2000_01 values less than (to_date('2000-01', 'yyyy-mm')),
partition part_2000_02 values less than (to_date('2000-02', 'yyyy-mm')),
partition part_2000_03 values less than (to_date('2000-03', 'yyyy-mm')),
partition part_2000_04 values less than (to_date('2000-04', 'yyyy-mm')),
partition part_2000_05 values less than (to_date('2000-05', 'yyyy-mm')),
partition part_2000_06 values less than (to_date('2000-06', 'yyyy-mm')),
partition part_2000_07 values less than (to_date('2000-07', 'yyyy-mm')),
partition part_2000_08 values less than (to_date('2000-08', 'yyyy-mm')),
partition part_2000_09 values less than (to_date('2000-09', 'yyyy-mm')),
partition part_2000_10 values less than (to_date('2000-10', 'yyyy-mm')),
partition part_2000_11 values less than (to_date('2000-11', 'yyyy-mm')),
partition part_2000_12 values less than (to_date('2000-12', 'yyyy-mm')),
partition part_2001_01 values less than (to_date('2001-01', 'yyyy-mm')),
partition part_2001_02 values less than (to_date('2001-02', 'yyyy-mm')),
partition part_2001_03 values less than (to_date('2001-03', 'yyyy-mm')),
partition part_2001_04 values less than (to_date('2001-04', 'yyyy-mm')),
partition part_2001_05 values less than (to_date('2001-05', 'yyyy-mm')),
partition part_2001_06 values less than (to_date('2001-06', 'yyyy-mm')),
partition part_2001_07 values less than (to_date('2001-07', 'yyyy-mm')),
partition part_2001_08 values less than (to_date('2001-08', 'yyyy-mm')),
partition part_2001_09 values less than (to_date('2001-09', 'yyyy-mm')),
partition part_2001_10 values less than (to_date('2001-10', 'yyyy-mm')),
partition part_2001_11 values less than (to_date('2001-11', 'yyyy-mm')),
partition part_2001_12 values less than (to_date('2001-12', 'yyyy-mm')),
partition part_2002_01 values less than (to_date('2002-01', 'yyyy-mm')),
partition part_2002_02 values less than (to_date('2002-02', 'yyyy-mm')),
partition part_2002_03 values less than (to_date('2002-03', 'yyyy-mm')),
partition part_2002_04 values less than (to_date('2002-04', 'yyyy-mm')),
partition part_2002_05 values less than (to_date('2002-05', 'yyyy-mm')),
partition part_2002_06 values less than (to_date('2002-06', 'yyyy-mm')),
partition part_2002_07 values less than (to_date('2002-07', 'yyyy-mm')),
partition part_2002_08 values less than (to_date('2002-08', 'yyyy-mm')),
partition part_2002_09 values less than (to_date('2002-09', 'yyyy-mm')),
partition part_2002_10 values less than (to_date('2002-10', 'yyyy-mm')),
partition part_2002_11 values less than (to_date('2002-11', 'yyyy-mm')),
partition part_2002_12 values less than (to_date('2002-12', 'yyyy-mm')),
partition part_2003_01 values less than (to_date('2003-01', 'yyyy-mm')),
partition part_2003_02 values less than (to_date('2003-02', 'yyyy-mm')),
partition part_2003_03 values less than (to_date('2003-03', 'yyyy-mm')),
partition part_2003_04 values less than (to_date('2003-04', 'yyyy-mm')),
partition part_2003_05 values less than (to_date('2003-05', 'yyyy-mm')),
partition part_2003_06 values less than (to_date('2003-06', 'yyyy-mm')),
partition part_2003_07 values less than (to_date('2003-07', 'yyyy-mm')),
partition part_2003_08 values less than (to_date('2003-08', 'yyyy-mm')),
partition part_2003_09 values less than (to_date('2003-09', 'yyyy-mm')),
partition part_2003_10 values less than (to_date('2003-10', 'yyyy-mm')),
partition part_2003_11 values less than (to_date('2003-11', 'yyyy-mm')),
partition part_2003_12 values less than (to_date('2003-12', 'yyyy-mm')),
partition part_2004_01 values less than (to_date('2004-01', 'yyyy-mm')),
partition part_2004_02 values less than (to_date('2004-02', 'yyyy-mm')),
partition part_2004_03 values less than (to_date('2004-03', 'yyyy-mm')),
partition part_2004_04 values less than (to_date('2004-04', 'yyyy-mm')),
partition part_2004_05 values less than (to_date('2004-05', 'yyyy-mm')),
partition part_2004_06 values less than (to_date('2004-06', 'yyyy-mm')),
partition part_2004_07 values less than (to_date('2004-07', 'yyyy-mm')),
partition part_2004_08 values less than (to_date('2004-08', 'yyyy-mm')),
partition part_2004_09 values less than (to_date('2004-09', 'yyyy-mm')),
partition part_2004_10 values less than (to_date('2004-10', 'yyyy-mm')),
partition part_2004_11 values less than (to_date('2004-11', 'yyyy-mm')),
partition part_2004_12 values less than (to_date('2004-12', 'yyyy-mm')),
partition part_2005_01 values less than (to_date('2005-01', 'yyyy-mm')),
partition part_2005_02 values less than (to_date('2005-02', 'yyyy-mm')),
partition part_2005_03 values less than (to_date('2005-03', 'yyyy-mm')),
partition part_2005_04 values less than (to_date('2005-04', 'yyyy-mm')),
partition part_2005_05 values less than (to_date('2005-05', 'yyyy-mm')),
partition part_2005_06 values less than (to_date('2005-06', 'yyyy-mm')),
partition part_2005_07 values less than (to_date('2005-07', 'yyyy-mm')),
partition part_2005_08 values less than (to_date('2005-08', 'yyyy-mm')),
partition part_2005_09 values less than (to_date('2005-09', 'yyyy-mm')),
partition part_2005_10 values less than (to_date('2005-10', 'yyyy-mm')),
partition part_2005_11 values less than (to_date('2005-11', 'yyyy-mm')),
partition part_2005_12 values less than (to_date('2005-12', 'yyyy-mm')),
partition part_2006_01 values less than (to_date('2006-01', 'yyyy-mm')),
partition part_2006_02 values less than (to_date('2006-02', 'yyyy-mm')),
partition part_2006_03 values less than (to_date('2006-03', 'yyyy-mm')),
partition part_2006_04 values less than (to_date('2006-04', 'yyyy-mm')),
partition part_2006_05 values less than (to_date('2006-05', 'yyyy-mm')),
partition part_2006_06 values less than (to_date('2006-06', 'yyyy-mm')),
partition part_2006_07 values less than (to_date('2006-07', 'yyyy-mm')),
partition part_2006_08 values less than (to_date('2006-08', 'yyyy-mm')),
partition part_2006_09 values less than (to_date('2006-09', 'yyyy-mm')),
partition part_2006_10 values less than (to_date('2006-10', 'yyyy-mm')),
partition part_2006_11 values less than (to_date('2006-11', 'yyyy-mm')),
partition part_2006_12 values less than (to_date('2006-12', 'yyyy-mm')),
partition part_2007_01 values less than (to_date('2007-01', 'yyyy-mm')),
partition part_2007_02 values less than (to_date('2007-02', 'yyyy-mm')),
partition part_2007_03 values less than (to_date('2007-03', 'yyyy-mm')),
partition part_2007_04 values less than (to_date('2007-04', 'yyyy-mm')),
partition part_2007_05 values less than (to_date('2007-05', 'yyyy-mm')),
partition part_2007_06 values less than (to_date('2007-06', 'yyyy-mm')),
partition part_2007_07 values less than (to_date('2007-07', 'yyyy-mm')),
partition part_2007_08 values less than (to_date('2007-08', 'yyyy-mm')),
partition part_2007_09 values less than (to_date('2007-09', 'yyyy-mm')),
partition part_2007_10 values less than (to_date('2007-10', 'yyyy-mm')),
partition part_2007_11 values less than (to_date('2007-11', 'yyyy-mm')),
partition part_2007_12 values less than (to_date('2007-12', 'yyyy-mm')),
partition part_2008_01 values less than (to_date('2008-01', 'yyyy-mm')),
partition part_2008_02 values less than (to_date('2008-02', 'yyyy-mm')),
partition part_2008_03 values less than (to_date('2008-03', 'yyyy-mm')),
partition part_2008_04 values less than (to_date('2008-04', 'yyyy-mm')),
partition part_2008_05 values less than (to_date('2008-05', 'yyyy-mm')),
partition part_2008_06 values less than (to_date('2008-06', 'yyyy-mm')),
partition part_2008_07 values less than (to_date('2008-07', 'yyyy-mm')),
partition part_2008_08 values less than (to_date('2008-08', 'yyyy-mm')),
partition part_2008_09 values less than (to_date('2008-09', 'yyyy-mm')),
partition part_2008_10 values less than (to_date('2008-10', 'yyyy-mm')),
partition part_2008_11 values less than (to_date('2008-11', 'yyyy-mm')),
partition part_2008_12 values less than (to_date('2008-12', 'yyyy-mm')),
partition part_2009_01 values less than (to_date('2009-01', 'yyyy-mm')),
partition part_2009_02 values less than (to_date('2009-02', 'yyyy-mm')),
partition part_2009_03 values less than (to_date('2009-03', 'yyyy-mm')),
partition part_2009_04 values less than (to_date('2009-04', 'yyyy-mm')),
partition part_2009_05 values less than (to_date('2009-05', 'yyyy-mm')),
partition part_2009_06 values less than (to_date('2009-06', 'yyyy-mm')),
partition part_2009_07 values less than (to_date('2009-07', 'yyyy-mm')),
partition part_2009_08 values less than (to_date('2009-08', 'yyyy-mm')),
partition part_2009_09 values less than (to_date('2009-09', 'yyyy-mm')),
partition part_2009_10 values less than (to_date('2009-10', 'yyyy-mm')),
partition part_2009_11 values less than (to_date('2009-11', 'yyyy-mm')),
partition part_2009_12 values less than (to_date('2009-12', 'yyyy-mm')),
partition part_2010_01 values less than (to_date('2010-01', 'yyyy-mm')),
partition part_2010_02 values less than (to_date('2010-02', 'yyyy-mm')),
partition part_2010_03 values less than (to_date('2010-03', 'yyyy-mm')),
partition part_2010_04 values less than (to_date('2010-04', 'yyyy-mm')),
partition part_2010_05 values less than (to_date('2010-05', 'yyyy-mm')),
partition part_2010_06 values less than (to_date('2010-06', 'yyyy-mm')),
partition part_2010_07 values less than (to_date('2010-07', 'yyyy-mm')),
partition part_2010_08 values less than (to_date('2010-08', 'yyyy-mm')),
partition part_2010_09 values less than (to_date('2010-09', 'yyyy-mm')),
partition part_2010_10 values less than (to_date('2010-10', 'yyyy-mm')),
partition part_2010_11 values less than (to_date('2010-11', 'yyyy-mm')),
partition part_2010_12 values less than (to_date('2010-12', 'yyyy-mm')),
partition part_2011_01 values less than (to_date('2011-01', 'yyyy-mm')),
partition part_2011_02 values less than (to_date('2011-02', 'yyyy-mm')),
partition part_2011_03 values less than (to_date('2011-03', 'yyyy-mm')),
partition part_2011_04 values less than (to_date('2011-04', 'yyyy-mm')),
partition part_2011_05 values less than (to_date('2011-05', 'yyyy-mm')),
partition part_2011_06 values less than (to_date('2011-06', 'yyyy-mm')),
partition part_2011_07 values less than (to_date('2011-07', 'yyyy-mm')),
partition part_2011_08 values less than (to_date('2011-08', 'yyyy-mm')),
partition part_2011_09 values less than (to_date('2011-09', 'yyyy-mm')),
partition part_2011_10 values less than (to_date('2011-10', 'yyyy-mm')),
partition part_2011_11 values less than (to_date('2011-11', 'yyyy-mm')),
partition part_2011_12 values less than (to_date('2011-12', 'yyyy-mm')),
partition part_2012_01 values less than (to_date('2012-01', 'yyyy-mm')),
partition part_2012_02 values less than (to_date('2012-02', 'yyyy-mm')),
partition part_2012_03 values less than (to_date('2012-03', 'yyyy-mm')),
partition part_2012_04 values less than (to_date('2012-04', 'yyyy-mm')),
partition part_2012_05 values less than (to_date('2012-05', 'yyyy-mm')),
partition part_2012_06 values less than (to_date('2012-06', 'yyyy-mm')),
partition part_2012_07 values less than (to_date('2012-07', 'yyyy-mm')),
partition part_2012_08 values less than (to_date('2012-08', 'yyyy-mm')),
partition part_2012_09 values less than (to_date('2012-09', 'yyyy-mm')),
partition part_2012_10 values less than (to_date('2012-10', 'yyyy-mm')),
partition part_2012_11 values less than (to_date('2012-11', 'yyyy-mm')),
partition part_2012_12 values less than (to_date('2012-12', 'yyyy-mm')),
partition part_2013_01 values less than (to_date('2013-01', 'yyyy-mm')),
partition part_2013_02 values less than (to_date('2013-02', 'yyyy-mm')),
partition part_2013_03 values less than (to_date('2013-03', 'yyyy-mm')),
partition part_2013_04 values less than (to_date('2013-04', 'yyyy-mm')),
partition part_2013_05 values less than (to_date('2013-05', 'yyyy-mm')),
partition part_2013_06 values less than (to_date('2013-06', 'yyyy-mm')),
partition part_2013_07 values less than (to_date('2013-07', 'yyyy-mm')),
partition part_2013_08 values less than (to_date('2013-08', 'yyyy-mm')),
partition part_2013_09 values less than (to_date('2013-09', 'yyyy-mm')),
partition part_2013_10 values less than (to_date('2013-10', 'yyyy-mm')),
partition part_2013_11 values less than (to_date('2013-11', 'yyyy-mm')),
partition part_2013_12 values less than (to_date('2013-12', 'yyyy-mm')),
partition part_2014_01 values less than (to_date('2014-01', 'yyyy-mm')),
partition part_2014_02 values less than (to_date('2014-02', 'yyyy-mm')),
partition part_2014_03 values less than (to_date('2014-03', 'yyyy-mm')),
partition part_2014_04 values less than (to_date('2014-04', 'yyyy-mm')),
partition part_2014_05 values less than (to_date('2014-05', 'yyyy-mm')),
partition part_2014_06 values less than (to_date('2014-06', 'yyyy-mm')),
partition part_2014_07 values less than (to_date('2014-07', 'yyyy-mm')),
partition part_2014_08 values less than (to_date('2014-08', 'yyyy-mm')),
partition part_2014_09 values less than (to_date('2014-09', 'yyyy-mm')),
partition part_2014_10 values less than (to_date('2014-10', 'yyyy-mm')),
partition part_2014_11 values less than (to_date('2014-11', 'yyyy-mm')),
partition part_2014_12 values less than (to_date('2014-12', 'yyyy-mm')),
partition part_2015_01 values less than (to_date('2015-01', 'yyyy-mm')),
partition part_2015_02 values less than (to_date('2015-02', 'yyyy-mm')),
partition part_2015_03 values less than (to_date('2015-03', 'yyyy-mm')),
partition part_2015_04 values less than (to_date('2015-04', 'yyyy-mm')),
partition part_2015_05 values less than (to_date('2015-05', 'yyyy-mm')),
partition part_2015_06 values less than (to_date('2015-06', 'yyyy-mm')),
partition part_2015_07 values less than (to_date('2015-07', 'yyyy-mm')),
partition part_2015_08 values less than (to_date('2015-08', 'yyyy-mm')),
partition part_2015_09 values less than (to_date('2015-09', 'yyyy-mm')),
partition part_2015_10 values less than (to_date('2015-10', 'yyyy-mm')),
partition part_2015_11 values less than (to_date('2015-11', 'yyyy-mm')),
partition part_2015_12 values less than (to_date('2015-12', 'yyyy-mm')),
partition part_2016_01 values less than (to_date('2016-01', 'yyyy-mm')),
partition part_2016_02 values less than (to_date('2016-02', 'yyyy-mm')),
partition part_2016_03 values less than (to_date('2016-03', 'yyyy-mm')),
partition part_2016_04 values less than (to_date('2016-04', 'yyyy-mm')),
partition part_2016_05 values less than (to_date('2016-05', 'yyyy-mm')),
partition part_2016_06 values less than (to_date('2016-06', 'yyyy-mm')),
partition part_2016_07 values less than (to_date('2016-07', 'yyyy-mm')),
partition part_2016_08 values less than (to_date('2016-08', 'yyyy-mm')),
partition part_2016_09 values less than (to_date('2016-09', 'yyyy-mm')),
partition part_2016_10 values less than (to_date('2016-10', 'yyyy-mm')),
partition part_2016_11 values less than (to_date('2016-11', 'yyyy-mm')),
partition part_2016_12 values less than (to_date('2016-12', 'yyyy-mm')),
partition part_2017_01 values less than (to_date('2017-01', 'yyyy-mm')),
partition part_2017_02 values less than (to_date('2017-02', 'yyyy-mm')),
partition part_2017_03 values less than (to_date('2017-03', 'yyyy-mm')),
partition part_2017_04 values less than (to_date('2017-04', 'yyyy-mm')),
partition part_2017_05 values less than (to_date('2017-05', 'yyyy-mm')),
partition part_2017_06 values less than (to_date('2017-06', 'yyyy-mm')),
partition part_2017_07 values less than (to_date('2017-07', 'yyyy-mm')),
partition part_2017_08 values less than (to_date('2017-08', 'yyyy-mm')),
partition part_2017_09 values less than (to_date('2017-09', 'yyyy-mm')),
partition part_2017_10 values less than (to_date('2017-10', 'yyyy-mm')),
partition part_2017_11 values less than (to_date('2017-11', 'yyyy-mm')),
partition part_2017_12 values less than (to_date('2017-12', 'yyyy-mm')),
partition part_2018_01 values less than (to_date('2018-01', 'yyyy-mm')),
partition part_2018_02 values less than (to_date('2018-02', 'yyyy-mm')),
partition part_2018_03 values less than (to_date('2018-03', 'yyyy-mm')),
partition part_2018_04 values less than (to_date('2018-04', 'yyyy-mm')),
partition part_2018_05 values less than (to_date('2018-05', 'yyyy-mm')),
partition part_2018_06 values less than (to_date('2018-06', 'yyyy-mm')),
partition part_2018_07 values less than (to_date('2018-07', 'yyyy-mm')),
partition part_2018_08 values less than (to_date('2018-08', 'yyyy-mm')),
partition part_2018_09 values less than (to_date('2018-09', 'yyyy-mm')),
partition part_2018_10 values less than (to_date('2018-10', 'yyyy-mm')),
partition part_2018_11 values less than (to_date('2018-11', 'yyyy-mm')),
partition part_2018_12 values less than (to_date('2018-12', 'yyyy-mm')),
partition part_2019_01 values less than (to_date('2019-01', 'yyyy-mm')),
partition part_2019_02 values less than (to_date('2019-02', 'yyyy-mm')),
partition part_2019_03 values less than (to_date('2019-03', 'yyyy-mm')),
partition part_2019_04 values less than (to_date('2019-04', 'yyyy-mm')),
partition part_2019_05 values less than (to_date('2019-05', 'yyyy-mm')),
partition part_2019_06 values less than (to_date('2019-06', 'yyyy-mm')),
partition part_2019_07 values less than (to_date('2019-07', 'yyyy-mm')),
partition part_2019_08 values less than (to_date('2019-08', 'yyyy-mm')),
partition part_2019_09 values less than (to_date('2019-09', 'yyyy-mm')),
partition part_2019_10 values less than (to_date('2019-10', 'yyyy-mm')),
partition part_2019_11 values less than (to_date('2019-11', 'yyyy-mm')),
partition part_2019_12 values less than (to_date('2019-12', 'yyyy-mm')),
partition part_2020_01 values less than (to_date('2020-01', 'yyyy-mm')),
partition part_2020_02 values less than (to_date('2020-02', 'yyyy-mm')),
partition part_2020_03 values less than (to_date('2020-03', 'yyyy-mm')),
partition part_2020_04 values less than (to_date('2020-04', 'yyyy-mm')),
partition part_2020_05 values less than (to_date('2020-05', 'yyyy-mm')),
partition part_2020_06 values less than (to_date('2020-06', 'yyyy-mm')),
partition part_2020_07 values less than (to_date('2020-07', 'yyyy-mm')),
partition part_2020_08 values less than (to_date('2020-08', 'yyyy-mm')),
partition part_2020_09 values less than (to_date('2020-09', 'yyyy-mm')),
partition part_2020_10 values less than (to_date('2020-10', 'yyyy-mm')),
partition part_2020_11 values less than (to_date('2020-11', 'yyyy-mm')),
partition part_2020_12 values less than (to_date('2020-12', 'yyyy-mm')),
partition part_2021_01 values less than (to_date('2021-01', 'yyyy-mm')),
partition part_2021_02 values less than (to_date('2021-02', 'yyyy-mm')),
partition part_2021_03 values less than (to_date('2021-03', 'yyyy-mm')),
partition part_2021_04 values less than (to_date('2021-04', 'yyyy-mm')),
partition part_2021_05 values less than (to_date('2021-05', 'yyyy-mm')),
partition part_2021_06 values less than (to_date('2021-06', 'yyyy-mm')),
partition part_2021_07 values less than (to_date('2021-07', 'yyyy-mm')),
partition part_2021_08 values less than (to_date('2021-08', 'yyyy-mm')),
partition part_2021_09 values less than (to_date('2021-09', 'yyyy-mm')),
partition part_2021_10 values less than (to_date('2021-10', 'yyyy-mm')),
partition part_2021_11 values less than (to_date('2021-11', 'yyyy-mm')),
partition part_2021_12 values less than (to_date('2021-12', 'yyyy-mm')),
partition part_2022_01 values less than (to_date('2022-01', 'yyyy-mm')),
partition part_2022_02 values less than (to_date('2022-02', 'yyyy-mm')),
partition part_2022_03 values less than (to_date('2022-03', 'yyyy-mm')),
partition part_2022_04 values less than (to_date('2022-04', 'yyyy-mm')),
partition part_2022_05 values less than (to_date('2022-05', 'yyyy-mm')),
partition part_2022_06 values less than (to_date('2022-06', 'yyyy-mm')),
partition part_2022_07 values less than (to_date('2022-07', 'yyyy-mm')),
partition part_2022_08 values less than (to_date('2022-08', 'yyyy-mm')),
partition part_2022_09 values less than (to_date('2022-09', 'yyyy-mm')),
partition part_2022_10 values less than (to_date('2022-10', 'yyyy-mm')),
partition part_2022_11 values less than (to_date('2022-11', 'yyyy-mm')),
partition part_2022_12 values less than (to_date('2022-12', 'yyyy-mm')),
partition part_2023_01 values less than (to_date('2023-01', 'yyyy-mm')),
partition part_2023_02 values less than (to_date('2023-02', 'yyyy-mm')),
partition part_2023_03 values less than (to_date('2023-03', 'yyyy-mm')),
partition part_2023_04 values less than (to_date('2023-04', 'yyyy-mm')),
partition part_2023_05 values less than (to_date('2023-05', 'yyyy-mm')),
partition part_2023_06 values less than (to_date('2023-06', 'yyyy-mm')),
partition part_2023_07 values less than (to_date('2023-07', 'yyyy-mm')),
partition part_2023_08 values less than (to_date('2023-08', 'yyyy-mm')),
partition part_2023_09 values less than (to_date('2023-09', 'yyyy-mm')),
partition part_2023_10 values less than (to_date('2023-10', 'yyyy-mm')),
partition part_2023_11 values less than (to_date('2023-11', 'yyyy-mm')),
partition part_2023_12 values less than (to_date('2023-12', 'yyyy-mm')),
partition part_2024_01 values less than (to_date('2024-01', 'yyyy-mm')),
partition part_2024_02 values less than (to_date('2024-02', 'yyyy-mm')),
partition part_2024_03 values less than (to_date('2024-03', 'yyyy-mm')),
partition part_2024_04 values less than (to_date('2024-04', 'yyyy-mm')),
partition part_2024_05 values less than (to_date('2024-05', 'yyyy-mm')),
partition part_2024_06 values less than (to_date('2024-06', 'yyyy-mm')),
partition part_2024_07 values less than (to_date('2024-07', 'yyyy-mm')),
partition part_2024_08 values less than (to_date('2024-08', 'yyyy-mm')),
partition part_2024_09 values less than (to_date('2024-09', 'yyyy-mm')),
partition part_2024_10 values less than (to_date('2024-10', 'yyyy-mm')),
partition part_2024_11 values less than (to_date('2024-11', 'yyyy-mm')),
partition part_2024_12 values less than (to_date('2024-12', 'yyyy-mm')),
partition part_2025_01 values less than (to_date('2025-01', 'yyyy-mm')),
partition part_2025_02 values less than (to_date('2025-02', 'yyyy-mm')),
partition part_2025_03 values less than (to_date('2025-03', 'yyyy-mm')),
partition part_2025_04 values less than (to_date('2025-04', 'yyyy-mm')),
partition part_2025_05 values less than (to_date('2025-05', 'yyyy-mm')),
partition part_2025_06 values less than (to_date('2025-06', 'yyyy-mm')),
partition part_2025_07 values less than (to_date('2025-07', 'yyyy-mm')),
partition part_2025_08 values less than (to_date('2025-08', 'yyyy-mm')),
partition part_2025_09 values less than (to_date('2025-09', 'yyyy-mm')),
partition part_2025_10 values less than (to_date('2025-10', 'yyyy-mm')),
partition part_2025_11 values less than (to_date('2025-11', 'yyyy-mm')),
partition part_2025_12 values less than (to_date('2025-12', 'yyyy-mm')),
partition part_2026_01 values less than (to_date('2026-01', 'yyyy-mm')),
partition part_2026_02 values less than (to_date('2026-02', 'yyyy-mm')),
partition part_2026_03 values less than (to_date('2026-03', 'yyyy-mm')),
partition part_2026_04 values less than (to_date('2026-04', 'yyyy-mm')),
partition part_2026_05 values less than (to_date('2026-05', 'yyyy-mm')),
partition part_2026_06 values less than (to_date('2026-06', 'yyyy-mm')),
partition part_2026_07 values less than (to_date('2026-07', 'yyyy-mm')),
partition part_2026_08 values less than (to_date('2026-08', 'yyyy-mm')),
partition part_2026_09 values less than (to_date('2026-09', 'yyyy-mm')),
partition part_2026_10 values less than (to_date('2026-10', 'yyyy-mm')),
partition part_2026_11 values less than (to_date('2026-11', 'yyyy-mm')),
partition part_2026_12 values less than (to_date('2026-12', 'yyyy-mm')),
partition part_2027_01 values less than (to_date('2027-01', 'yyyy-mm')),
partition part_2027_02 values less than (to_date('2027-02', 'yyyy-mm')),
partition part_2027_03 values less than (to_date('2027-03', 'yyyy-mm')),
partition part_2027_04 values less than (to_date('2027-04', 'yyyy-mm')),
partition part_2027_05 values less than (to_date('2027-05', 'yyyy-mm')),
partition part_2027_06 values less than (to_date('2027-06', 'yyyy-mm')),
partition part_2027_07 values less than (to_date('2027-07', 'yyyy-mm')),
partition part_2027_08 values less than (to_date('2027-08', 'yyyy-mm')),
partition part_2027_09 values less than (to_date('2027-09', 'yyyy-mm')),
partition part_2027_10 values less than (to_date('2027-10', 'yyyy-mm')),
partition part_2027_11 values less than (to_date('2027-11', 'yyyy-mm')),
partition part_2027_12 values less than (to_date('2027-12', 'yyyy-mm')),
partition part_2028_01 values less than (to_date('2028-01', 'yyyy-mm')),
partition part_2028_02 values less than (to_date('2028-02', 'yyyy-mm')),
partition part_2028_03 values less than (to_date('2028-03', 'yyyy-mm')),
partition part_2028_04 values less than (to_date('2028-04', 'yyyy-mm')),
partition part_2028_05 values less than (to_date('2028-05', 'yyyy-mm')),
partition part_2028_06 values less than (to_date('2028-06', 'yyyy-mm')),
partition part_2028_07 values less than (to_date('2028-07', 'yyyy-mm')),
partition part_2028_08 values less than (to_date('2028-08', 'yyyy-mm')),
partition part_2028_09 values less than (to_date('2028-09', 'yyyy-mm')),
partition part_2028_10 values less than (to_date('2028-10', 'yyyy-mm')),
partition part_2028_11 values less than (to_date('2028-11', 'yyyy-mm')),
partition part_2028_12 values less than (to_date('2028-12', 'yyyy-mm')),
partition part_2029_01 values less than (to_date('2029-01', 'yyyy-mm')),
partition part_2029_02 values less than (to_date('2029-02', 'yyyy-mm')),
partition part_2029_03 values less than (to_date('2029-03', 'yyyy-mm')),
partition part_2029_04 values less than (to_date('2029-04', 'yyyy-mm')),
partition part_2029_05 values less than (to_date('2029-05', 'yyyy-mm')),
partition part_2029_06 values less than (to_date('2029-06', 'yyyy-mm')),
partition part_2029_07 values less than (to_date('2029-07', 'yyyy-mm')),
partition part_2029_08 values less than (to_date('2029-08', 'yyyy-mm')),
partition part_2029_09 values less than (to_date('2029-09', 'yyyy-mm')),
partition part_2029_10 values less than (to_date('2029-10', 'yyyy-mm')),
partition part_2029_11 values less than (to_date('2029-11', 'yyyy-mm')),
partition part_2029_12 values less than (to_date('2029-12', 'yyyy-mm')),
partition part_2030_01 values less than (to_date('2030-01', 'yyyy-mm')),
partition part_2030_02 values less than (to_date('2030-02', 'yyyy-mm')),
partition part_2030_03 values less than (to_date('2030-03', 'yyyy-mm')),
partition part_2030_04 values less than (to_date('2030-04', 'yyyy-mm')),
partition part_2030_05 values less than (to_date('2030-05', 'yyyy-mm')),
partition part_2030_06 values less than (to_date('2030-06', 'yyyy-mm')),
partition part_2030_07 values less than (to_date('2030-07', 'yyyy-mm')),
partition part_2030_08 values less than (to_date('2030-08', 'yyyy-mm')),
partition part_2030_09 values less than (to_date('2030-09', 'yyyy-mm')),
partition part_2030_10 values less than (to_date('2030-10', 'yyyy-mm')),
partition part_2030_11 values less than (to_date('2030-11', 'yyyy-mm')),
partition part_2030_12 values less than (to_date('2030-12', 'yyyy-mm')),
partition part_maxvalue values less than (maxvalue)

)
