package ru.iyshcherbakov.dntestapp.data.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.iyshcherbakov.dntestapp.data.AppDatabase
import ru.iyshcherbakov.dntestapp.data.NodeDao
import ru.iyshcherbakov.dntestapp.data.NodeRepositoryImpl
import ru.iyshcherbakov.dntestapp.domain.AddChildNodeUseCase
import ru.iyshcherbakov.dntestapp.domain.DeleteNodeUseCase
import ru.iyshcherbakov.dntestapp.domain.GenerateNodeNameUseCase
import ru.iyshcherbakov.dntestapp.domain.GetNodeUseCase
import ru.iyshcherbakov.dntestapp.domain.GetRootNodeUseCase
import ru.iyshcherbakov.dntestapp.domain.NodeRepository
import ru.iyshcherbakov.dntestapp.domain.SaveNodeUseCase

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideNodeDao(app: Application): NodeDao {
        return AppDatabase.getInstance(app).nodeDao()
    }

    @Provides
    fun provideNodeRepository(dao: NodeDao): NodeRepository {
        return NodeRepositoryImpl(dao)
    }

    @Provides
    fun provideGetRootNode(repo: NodeRepository): GetRootNodeUseCase {
        return GetRootNodeUseCase(repo)
    }
    @Provides
    fun provideDeleteNode(repo: NodeRepository): DeleteNodeUseCase {
        return DeleteNodeUseCase(repo)
    }
    @Provides
    fun provideAddChildNode(repo: NodeRepository): AddChildNodeUseCase {
        return AddChildNodeUseCase(repo)
    }
    @Provides
    fun provideSaveNode(repo: NodeRepository): SaveNodeUseCase {
        return SaveNodeUseCase(repo)
    }
    @Provides
    fun provideGenerateNodeName(repo: NodeRepository): GenerateNodeNameUseCase {
        return GenerateNodeNameUseCase(repo)
    }
    @Provides
    fun provideGetNode(repo: NodeRepository): GetNodeUseCase {
        return GetNodeUseCase(repo)
    }
}