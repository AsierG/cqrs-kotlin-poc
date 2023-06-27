# CQRS POC

> ✉️ Foundations to build CQRS/EDA architectures in easy way

## ℹ️ Introduction
Complete practical example of the use of domain-based design (DDD), hexagonal architecture, CQRS and event-driven architecture (EDA) using Kotlin and SpringBoot.

## 💡 Prerequisites 

* Java 17 (required)

## 🚀 Getting Started

A command and query bus allows to separate modification operations and data retrieval operations in an application. This allows if required two different databases using CQRS, one database for modification operations and one database for query operations.

### Command

You need to define the command message and the command handler:

```kotlin

data class ExampleCommand(
        val exampleId: UUID,
) : Command()

class Example : CommandHandler<ExampleCommand> {

    override fun handle(command: ExampleCommand) {
        // do something
    }

}
```

Usage:

```kotlin

 class ExampleController(private val commandBus: CommandBus) {

    fun example() {
        commandBus.dispatch(
                ExampleCommand(exampleId = UUID.randomUUID()),
        )
    }
}
```

### Query

You need to define the query message and the query handler. Setting the Query generic `Query<T>`, you enable the query
bus typed response:

```kotlin
data class ExampleQuery(
    val exampleId: UUID,
) : Query<ExampleView>()


class ExampleQueryHandler() : QueryHandler<ExampleView, ExampleQuery> {

    override fun handle(query: ExampleQuery): ExampleView {
        return ExampleView()
    }
}
```

Usage:

```kotlin
 class ExampleController(private val queryBus: QueryBus) {

    fun example():ExampleView {
        val conversation = queryBus.ask(
                ExampleQuery(exampleId = UUID.fromString("d5b9a6be-2eb6-4735-a256-8eab22a4461b"))
        )
    }
}
```

An event bus is the mechanism by which DDD domain events are published, processed and sent to their receivers directly, via middleware or otherwise. The event bus concept for domain events is very simply realized in code, simply an interface with a method. By changing the implementation of the interface an event bus sends the events to a messaging system such as SQS, RabbitMQ, Kafka... persists the events in the database as part of the outbox pattern or simply store in memory as in the example

### Domain Events

Sometimes your application will want to expose information about domain events. 

It will be necessary to create a class that extends DomainEvent and then in the Aggregate we will add the event:

Finally, we should send the events in the event bus.

```kotlin
data class ExampleCreated(
    val aggregateId: UUID,
    val exampleValue: String,
) : DomainEvent(aggregateId)


data class Example(
        val exampleId: ExampleId,
        val createdOn: CreatedOn,
        ) : AggregateRoot() {

        fun doSomething() {
            this.record(
                    ExampleCreated(
                            aggregateId = this.exampleId.value,
                            exampleValue = "aabbcc",
                    ),
            )
        }

}

class ExampleHandler(private val eventBus: EventBus) : CommandHandler<ExampleCommand> {

    override fun handle(command: ExampleCommand) {
        val example = Example().doSomething()

        eventBus.publish(example.recordedEvents())
    }
    
}
```
