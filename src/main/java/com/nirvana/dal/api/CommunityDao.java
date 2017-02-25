package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Community;
/**
 * 
 * @author Bin
 * 社区数据库访问层
 */
@Repository
public interface CommunityDao extends JpaRepository<Community, Integer>{
	/**
	 * 根据社区名以及社区地址模糊查询社区 按照社区名gbk排序
	 * @param key 搜索值
	 * @return 符合社区集合
	 */
	@Query(nativeQuery=true,value="SELECT * FROM community WHERE communityname LIKE %:key% OR communitylocation LIKE %:key% ORDER BY CONVERT(communityname USING gbk) ASC")
	List<Community> fuzzyQueryOrderByGBK(@Param("key") String key);

	/**
	 * 查询所有的社区 按照社区名gbk排序
	 * @return 社区集合
	 */
	@Query(nativeQuery=true,value="SELECT * FROM community ORDER BY CONVERT(communityname USING gbk) ASC")
	List<Community> findAllOrderByGBK();
}
